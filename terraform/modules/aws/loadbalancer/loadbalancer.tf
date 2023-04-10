
## alb area 
# ALB

resource "aws_lb" "web-alb" {
    name = var.lb_name
    internal           = var.lb_internal
    load_balancer_type = var.lb_type
    security_groups    = var.security_groups
    subnets            = var.subnets
    tags               = var.tag_name
    enable_cross_zone_load_balancing = true
}

# Target Group
resource "aws_lb_target_group" "web-alb-tg" {
    name     = var.lb_target_group_name
    port     = var.port
    protocol = var.lb_protocol
    vpc_id   = var.vpc_id
    tags     = var.tg_tag_name
    lifecycle {
        create_before_destroy = true
    }
    health_check {
        path                = "/"
        protocol            = "HTTP"
        matcher             = "200"
        interval            = 15
        timeout             = 3
        healthy_threshold   = 2
        unhealthy_threshold = 2
    }
}


resource "aws_lb_listener" "web-alb-listen" {
    load_balancer_arn =  aws_lb.web-alb.arn
    port = 80
    protocol = "HTTP"

    default_action {
        type = "forward"
        target_group_arn =  aws_lb_target_group.web-alb-tg.arn
        fixed_response {
            content_type = "text/plain"
            message_body = "404: 페이지가 안나온다!"
            status_code  = 404
        }
    }
}



resource "aws_lb_listener_rule" "web-alb-listener-rule" {
  listener_arn = aws_lb_listener.web-alb-listen.arn
  priority     = 100

  condition {
    path_pattern {
      values = ["*"]
    }
  }

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.web-alb-tg.arn
  }
}


