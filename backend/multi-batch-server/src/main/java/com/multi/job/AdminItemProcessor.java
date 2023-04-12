package com.multi.job;

import com.multi.domain.admin.Admin;
import org.springframework.batch.item.ItemProcessor;

public class AdminItemProcessor implements ItemProcessor<Admin, Admin> {

    @Override
    public Admin process(Admin Admin) throws Exception {
        Admin.setAdminName("Admin.getAge() + 10");
        return Admin;
    }
}
