output "default-public_ip" {
  value = aws_instance.instance-template-default[*].public_ip
}

output "default-private_ip" {
  value = aws_instance.instance-template-default[*].private_ip
}

output "default-ec2_id" {
  value = aws_instance.instance-template-default[*].id
}

output "ami-public_ip" {
  value = aws_instance.instance-template-ami[*].public_ip
}

output "ami-private_ip" {
  value = aws_instance.instance-template-ami[*].private_ip
}

output "ami-ec2_id" {
  value = aws_instance.instance-template-ami[*].id
}
