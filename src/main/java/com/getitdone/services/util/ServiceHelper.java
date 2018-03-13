package com.getitdone.services.util;

import com.getitdone.services.core.impl.ProjectServiceImpl;
import com.getitdone.services.domain.Project;
import com.getitdone.services.repo.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServiceHelper.class);

    @Autowired
    ProjectRepository repository;

    @Async
    public void doStatusChange(Project p) {
        try {

            if (p.getListingExpiryDate() != null){
                logger.info("list date list date: {}", p.getListingExpiryDate() );

                if( new Date().after(p.getListingExpiryDate())) {
                    p.setStatus(Project.STATUS.CLOSED.name());
                    repository.save(p);
                }
            }
        } catch (Exception e){
            logger.error("Error changing status: {} , exception: {}", p, e);
            throw new GetitDonAppException("code-statusChange", e.toString());
        }

    }
}
