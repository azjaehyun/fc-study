
output "ssh_private_key_pem" {
  value = module.aws_key_pair.ssh_private_key_pem
  sensitive = true
}

output "ssh_public_key_pem" {
  value = module.aws_key_pair.ssh_public_key_pem
  sensitive = true
}

output "jenkins_public_ip" {
  value =module.aws_ec2_public_jenkins_ec2.public_ip
}