output "aws_security_group_http_server_details" {
  value = aws_security_group.http_server_sg
}


output "jenkins_instance_public_ip" {
  value = aws_instance.instances.public_ip
}