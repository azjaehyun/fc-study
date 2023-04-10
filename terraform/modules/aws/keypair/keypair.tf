resource "tls_private_key" "ssh" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "ssh" {
  key_name   = var.keypair_name
  public_key = tls_private_key.ssh.public_key_openssh
  provisioner "local-exec" { # Create "myKey.pem" to your computer!!
    command = "echo '${tls_private_key.ssh.private_key_pem}' > ./${var.keypair_name}.pem"
  }
  tags = var.tag_name
}

#resource "local_file" "pem_file" {
#  filename = pathexpand("./${var.keypair_name}.pem")
#  file_permission = "600"
#  directory_permission = "700"
#  sensitive_content = tls_private_key.ssh.private_key_pem
#}

resource "local_sensitive_file" "pem_file" {
    content  = tls_private_key.ssh.private_key_pem
    filename = pathexpand("./${var.keypair_name}.pem")
}