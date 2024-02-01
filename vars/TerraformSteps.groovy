// vars/awsCredentials.groovy
def withAWSCredentials(Closure awsCreds) {
    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: '6466376b-9ed7-498e-92cb-9f21eb3aa540', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
        def awsAccessKey = env.AWS_ACCESS_KEY_ID
        def awsSecretKey = env.AWS_SECRET_ACCESS_KEY
        // Set AWS credentials as environment variables
        withEnv(["AWS_ACCESS_KEY_ID=${awsAccessKey}", "AWS_SECRET_ACCESS_KEY=${awsSecretKey}"]) {
            awsCreds()
        }
    }
}

def terraformInit() {
    dir(directory) {
        //Execute tfm init
        sh 'terraform init'
    }
}

def terraformValidateAndFormat() {
    dir(directory) {
        //validate terraform scripts
        sh 'terraform validate'

        //Formatting the terraform script
        //check=true  -> checks for files formatting
        //write=false -> this prevents the files from modifying the files
        //diff=true   -> checks for any differences from original file and formatted version
        sh 'terraform fmt -check=true -write=false -diff=true'
    }
}

def terraformPlan(planFileName = 'terraform.tfplan') {
    //execute and store terraform plan in a file
    //This includes optional arguement 'planFileName -> default = [terraform.tfplan]'
    //Orelse give the name for plan file -> 'custom-filename.tfplan'
    dir(directory) {
        sh "terraform plan -out=${planFileName}"
    }
}

