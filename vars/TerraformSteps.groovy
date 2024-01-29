def terraformInit() {
    //Execute tfm init
    sh 'terraform init'
}

def terraformValidateAndFormat() {
    //validate terraform scripts
    sh 'terraform validate'

    //Formatting the terraform script
    //check=true  -> checks for files formatting
    //write=false -> this prevents the files from modifying the files
    //diff=true   -> checks for any differences from original file and formatted version
    sh 'terraform fmt -check=true -write=false -diff=true'
}

def terraformPlan(planFileName = 'terraform.tfplan') {
    //execute and store terraform plan in a file
    //This includes optional arguement 'planFileName -> default = [terraform.tfplan]'
    //Orelse give the name for plan file -> 'custom-filename.tfplan'
    sh 'terraform plan -out=${planFileName}'
}

