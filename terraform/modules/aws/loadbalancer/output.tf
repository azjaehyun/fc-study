
output "lb-arn" {
  value = aws_lb.web-alb.arn
}

output "lb-tg-arn" {
  value = aws_lb_target_group.web-alb-tg.arn
}