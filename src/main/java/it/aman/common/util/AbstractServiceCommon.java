package it.aman.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import it.aman.common.exception.ERPException;
import it.aman.common.exception.ERPExceptionEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractServiceCommon {

    private final HttpServletRequest request;
    
    public String getLoggedInUserFromHeader() throws ERPException {
        final String userName = request.getHeader(ERPConstants.X_REQUEST_URL_SUBJECT);
        if (StringUtils.isBlank(userName)) {
            log.error("User name not found in the header.");
            throw ERPExceptionEnums.UNAUTHORIZED_EXCEPTION.get();
        }
        return userName;
    }
}
