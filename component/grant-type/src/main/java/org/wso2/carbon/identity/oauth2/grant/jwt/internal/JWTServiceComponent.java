/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License
 */
package org.wso2.carbon.identity.oauth2.grant.jwt.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.oauth2.grant.jwt.JWTBearerGrantHandler;
import org.wso2.carbon.identity.oauth2.grant.jwt.JWTGrantValidator;
import org.wso2.carbon.identity.oauth2.token.handlers.grant.AuthorizationGrantHandler;
import org.apache.oltu.oauth2.common.validators.OAuthValidator;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.Hashtable;

/**
 * @scr.component name="identity.oauth2.grant.jwt.component" immediate="true"
 */
public class JWTServiceComponent {
    private static Log log = LogFactory.getLog(JWTServiceComponent.class);

    protected void activate(ComponentContext ctxt) {
        try {
            JWTBearerGrantHandler grantHandler = new JWTBearerGrantHandler();
            Hashtable<String, String> props = new Hashtable<String, String>();
            ctxt.getBundleContext().registerService(AuthorizationGrantHandler.class.getName(),
                    grantHandler, props);

            JWTGrantValidator validator = new JWTGrantValidator();
            ctxt.getBundleContext().registerService(OAuthValidator.class.getName(), validator, props);
            if (log.isDebugEnabled()) {
                log.debug("JWT grant handler is activated");
            }
        } catch (Throwable e) {
            log.fatal("Error while activating the JWT grant handler ", e);
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("JWT grant handler is deactivated");
        }
    }

    public static RealmService getRealmService() {
        return (RealmService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(RealmService.class);
    }
}
