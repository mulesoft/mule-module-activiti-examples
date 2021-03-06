/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.activiti;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.test.Deployment;

public class ActivitiSalesforceTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    @Deployment
    public void testCreateContact() throws Exception {
        ProcessEngine processEngine = muleContext.getRegistry().get("processEngine");
        TestHelper.annotationDeploymentSetUp(processEngine, getClass(), getName());
        
        MuleClient client = muleContext.getClient();
        DefaultMuleMessage message = new DefaultMuleMessage("", muleContext);
        Map parameterMap = new HashMap();
        parameterMap.put("processDefinitionKey", "processLead");
        parameterMap.put("firstName", "John");
        parameterMap.put("lastName", "Perez");
        parameterMap.put("email", "john@perez.com");
        parameterMap.put("title", "Sr. Software Engineer");
        parameterMap.put("phone", "01231-114-123123");
        
        message.setProperty("createProcessParameters", parameterMap , PropertyScope.OUTBOUND);
        
        MuleMessage responseMessage = client.send("vm://in", message);
    }
}


