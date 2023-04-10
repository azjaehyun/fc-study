variable "cidr_block" {
  type = string
}
variable "is_public" {
  type    = bool
  default = false
}

variable "vpc_id" {
  type = string
}


variable "tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}

variable "availability_zone" {
  type = string
}
