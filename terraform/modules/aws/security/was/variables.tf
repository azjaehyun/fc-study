variable vpc_id {
  type = string
}

variable name {
  type    = string
  default = "default-key"
}


variable "tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}