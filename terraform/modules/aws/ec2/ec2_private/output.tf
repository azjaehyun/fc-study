

output "default-private_ip" {
  value = join(", ",aws_instance.instance-template-default[*].private_ip)
}

output "default-ec2_id" {
  value = join(", ",aws_instance.instance-template-default[*].id)
}


output "ami-private_ip" {
  value = join(", ",aws_instance.instance-template-ami[*].private_ip)
}

output "ami-ec2_id" {
  value = join(", ",aws_instance.instance-template-ami[*].id)
}
