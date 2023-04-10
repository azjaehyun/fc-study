resource "aws_instance" "instance-template-default" {
  count = format("%s", var.ami_id) == "" ? 1 : 0
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = var.instance_type
  vpc_security_group_ids = var.sg_groups
  subnet_id              = var.subnet_id
  key_name               = var.key_name
  tags = var.tag_name
}

resource "aws_instance" "instance-template-ami" {
  count =  format("%s", var.ami_id) != "" ? 1 : 0 
  ami                    = var.ami_id
  instance_type          = var.instance_type
  vpc_security_group_ids = var.sg_groups
  subnet_id              = var.subnet_id
  key_name               = var.key_name
  tags = var.tag_name
}
