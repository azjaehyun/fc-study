package com.multi.job;

import com.multi.domain.admin.AdminUser;
import org.springframework.batch.item.ItemProcessor;

public class AdminItemProcessor implements ItemProcessor<AdminUser, AdminUser> {

    @Override
    public AdminUser process(AdminUser adminUser) throws Exception {
        // Admin.setAdminName("Admin.getAge() + 10");
        return adminUser;
    }
}
