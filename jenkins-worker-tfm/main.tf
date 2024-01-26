terraform {
    backend "s3" {
        bucket = "django-docker-ec2-s3-backendBucket"
        key = "remoteBackend/jenkinsWorkerState"
        region = "us-east-1"
        dynamodb_table = "app_backend_lock"
        encrypt = true
    }
}

provider "aws" {
    region = "us-east-1"
}

# Vault Provider Configuration
provider "vault" {
  address = "http://127.0.0.1:8200"  # Update with your Vault server address
}