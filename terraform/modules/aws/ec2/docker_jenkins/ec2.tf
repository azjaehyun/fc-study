resource "aws_instance" "instance-template" {
    ami                    = data.aws_ami.ubuntu.id
    instance_type          = var.instance_type
    vpc_security_group_ids = var.sg_groups
    subnet_id              = var.subnet_id
    key_name               = var.key_name
    tags = var.tag_name
    associate_public_ip_address = var.public_access

    provisioner "file" {
      source      = "${path.module}/deploy_with_docker_inner_docker.sh"
      destination = "/tmp/script.sh"
    }

    connection {
      type        = "ssh"
      user        = "ubuntu"
      password    = ""
      private_key = file(var.key_path)
      host        = self.public_ip
    }
    provisioner "remote-exec" {
      inline = [
        "chmod +x /tmp/script.sh",
        "sudo /tmp/script.sh ${var.docker_image} ${var.in_port} ${var.out_port}",
      ]
    }
}