variable lb_type {
  type = string
  default = "application"
}

variable lb_internal {
  type = bool
  default = true
}

variable  lb_name {
    type = string
}

variable lb_target_group_name{
  type = string
}

variable lb_protocol {
  type = string
  default = "TCP"
}

variable vpc_id {
   type = string
}

variable port {
  type = number
  default = 80
}

variable target_port {
  type = number
  default = 80
}  

variable subnets {
  type = list(string)
  default = []
}

variable security_groups {
  type = list(string)
  default = []
}

variable "tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}


variable "tg_tag_name" {
  description = "A mapping of tags to assign to the resource"
  type        = map(string)
  default     = {}
}


variable nlb_listeners_ids {
  type = set(string)
  default = []
}

