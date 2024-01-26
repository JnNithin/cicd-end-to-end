//Import security group module 
module "security_group" {
  source = "./modules/Security-group"
}

variable "aws_key_pair" {
    default = "~/aws/aws_keys/pipeline-keys.pem
}

data "aws_subnets" "subnets" {
}

variable "desired_ami_id" {
    description = "The required AMI ID"
    default = "ami-0c7217cdde317cfec"
}

variable "instance_count" {
    description= "To select number of instances"
    default = 1
}

variable "subnet_id" {
  # Define your default subnet ID
  default = "subnet-0373cf7aaf2597384"
}

//This is configured in HashiCorp Vault and this is the path for it
//vault kv list secrets
data "vault_generic_secret" "jenkins_worker" {
  path = "secrets/jenkins-worker"
} 

//to use declared variable for AMI -> ${var.desired_ami_id}
//this is for ec2s
resource "aws_instance" "instances" {
    count         = var.instance_count
    ami           = var.desired_ami_id
    instance_type = "t2.micro"
    key_name      = var.aws_key_pair
    subnet_id     = var.subnet_id

    user_data = <<-EOF
        #!/bin/bash
        # Include the main script
        $(cat "${path.module}/scripts/jenkins-worker.sh")
        sudo systemctl start jenkins
    EOF

    tags = {
        Name = "Jenkins-Worker-Instance"
    }
}
