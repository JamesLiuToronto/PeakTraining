package com.peak.training.common.audit.api;

import com.peak.training.common.audit.dto.AuditLogDTO;
import com.peak.training.common.audit.service.AuditLogService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("peaktraining/audit")
@Tag(name = "Peak Training Audit Interface", description = "the API with audit interfaces")
public class AuditLogController {
    @Autowired
    AuditLogService service ;

    @GetMapping(value = "{auditId}",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AuditLogDTO getById(
            @Parameter(description="Audit Log Id.",
                    required=true)
            @PathParam("AuditId") int auditId ){

        return service.getAuditLogById(auditId);
    }

    @PostMapping(value = "{auditId}",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateAudit(
            @Parameter(description="AuditLogId.", required=true)
            @PathParam("auditId") int auditId ,
            @Parameter(description="userId", required=true)
            @RequestHeader("userId") int  userId ) {


        service.persistAuditLog(auditId, userId) ;
    }
}
