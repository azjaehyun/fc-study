variable "instance_type" {
  type    = string
  default = "t2.micro"
}

variable "sg_groups" {
  type = list(string)
}

variable "subnet_id" {
  type = string
}

variable "public_access" {
  type    = bool
  default = false
}

variable "ami_id" {
  type    = string
  default = ""
}

variable "name" {
  type    = string
  default = "myapp"
}

variable "key_name" {
  type = string
}


variable "tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}