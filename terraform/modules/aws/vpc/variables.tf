variable "cidr_block" {
  type = string
  # default = "10.10.0.0/16"
}

variable "tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}