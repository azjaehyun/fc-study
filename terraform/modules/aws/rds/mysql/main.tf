resource "aws_db_instance" "awesome-ap2-mysql" {
    allocated_storage    = 10
    engine               = "mysql"
    engine_version       = "5.7"
    instance_class       = "db.t2.micro"
    name                 = var.db_name
    username             = var.db_username
    password             = var.db_password
    parameter_group_name = "default.mysql5.7"
    skip_final_snapshot  = true
    publicly_accessible = false
    multi_az = true

    db_subnet_group_name   = aws_db_subnet_group.awesome-ap-db-sub-group.name
    vpc_security_group_ids = [aws_security_group.awesome-ap2-db-sg.id]

    tags = {
        "Name" = "awesome-ap2-mysql"
    }
}

resource "aws_db_subnet_group" "awesome-ap-db-sub-group" {
    name = "awesome-ap-db-sub-group"
    subnet_ids = [aws_subnet.awesome-ap-db-sub-2a.id, aws_subnet.awesome-ap-db-sub-2c.id]

    tags = {
        Name = "awesome-ap-db-sub-group"
    }
}