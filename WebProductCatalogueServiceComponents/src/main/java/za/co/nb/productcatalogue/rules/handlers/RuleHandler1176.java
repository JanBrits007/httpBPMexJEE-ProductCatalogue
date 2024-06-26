package za.co.nb.productcatalogue.rules.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import za.co.nb.onboarding.casemanagement.BusinessCaseManagementDAO;
import za.co.nb.onboarding.casemanagement.dto.BusinessCaseHeader;
import za.co.nb.onboarding.casemanagement.dto.BusinessCaseSegment;
import za.co.nb.productcatalogue.exceptions.BusinessRuleExecutionException;

public class RuleHandler1176 extends BaseProductSpecificationRuleHandler {

    private static final Log mLog = LogFactory.getLog(BaseProductSpecificationRuleHandler.class);

    /*@Override
    public String executeBusinessRules(String productIDToSubstitute, String caseID) throws BusinessRuleExecutionException {
        mLog.debug("Trace 1 >>" + productIDToSubstitute + "<<,>>" + caseID + "<<");

        // First get the list of arracgements allocated to the case.
        BusinessCaseManagementDAO dao = new BusinessCaseManagementDAO();

        BusinessCaseHeader businessCase = dao.retrieveBusinessCase(caseID);

        boolean isRRBClient = false;
        if(businessCase != null && businessCase.getClientInContextECN() != null && businessCase.getInitiatingChannelID() != null
            && "397".equalsIgnoreCase(businessCase.getInitiatingChannelID())){

            mLog.debug("Trace 2");
            SystemConfiguratorDAO systemConfiguratorDAO = new SystemConfiguratorDAO();
            try {

                mLog.debug("Trace 2.1");
                Environment environment = systemConfiguratorDAO.getEnvironment();
                String serviceUrl = environment.PARTY_MANAGEMENT_CLIENT_CHECK_URL.toString();
                //String serviceUrl ="http://localhost:9082/WebPartyManagement/branchmanagement/v1";
                StringBuilder builder = new StringBuilder();
                builder.append(serviceUrl);
                builder.append("/clientsegment?");
                boolean partyIdPresent = false;
                boolean bankerCodePresent = false;
                if(businessCase.getClientInContextECN() != null && !"".equalsIgnoreCase(businessCase.getClientInContextECN())){
                    partyIdPresent = true;
                    builder.append("partyid=");
                    builder.append(businessCase.getClientInContextECN());
                }
                if(businessCase.getInitiatingStaffNBNumber() != null && !"".equalsIgnoreCase(businessCase.getInitiatingStaffNBNumber())){

                    if(partyIdPresent){
                        builder.append("&");
                    }
                    bankerCodePresent = true;
                    builder.append("bankercode=");
                    builder.append(businessCase.getInitiatingStaffNBNumber());
                }
                if(caseID != null && !"".equalsIgnoreCase(caseID)){

                    if(partyIdPresent || bankerCodePresent){
                        builder.append("&");
                    }
                    builder.append("caseid=");
                    builder.append(caseID);
                }
                serviceUrl = builder.toString();
                mLog.debug("Trace 2.2 Service URL : "+serviceUrl);
                HashMap<String, String> requestProperties = new HashMap();
                requestProperties.put("Content-Type", "application/json");
                HttpClientUtil httpUtils = new HttpClientUtil();
                JSONObject response=httpUtils.sendGET(serviceUrl,requestProperties);

                if(response != null && response.get("resultSet") != null ){
                    String resultCode = (String)((JSONObject)response.get("resultSet")).get("resultCode");

                    mLog.debug("Trace 2.3");
                    if(resultCode.equalsIgnoreCase("R00")){
                        mLog.debug("Trace 2.4 : Is RRB Client :"+(boolean)((JSONObject)response.get("clientSegmentResponse")).get("rrbclient"));
                        isRRBClient = (boolean)((JSONObject)response.get("clientSegmentResponse")).get("rrbclient");

                        String cluster = (String)((JSONObject)response.get("clientSegmentResponse")).get("cluster");
                        String division = (String)((JSONObject)response.get("clientSegmentResponse")).get("division");
                        if((cluster != null && !"".equalsIgnoreCase(cluster)) ||
                                (division != null && !"".equalsIgnoreCase(division))){
                            BusinessCaseSegment businessCaseSegment = new BusinessCaseSegment();
                            if(cluster != null && !"".equalsIgnoreCase(cluster)){
                                businessCaseSegment.setCluster(cluster);
                            }
                            if(division != null && !"".equalsIgnoreCase(division)){
                                businessCaseSegment.setDivision(division);
                            }
                            businessCase.setBusinessCaseSegment(businessCaseSegment);
                            dao.cacheBusinessCaseDetailInDB(businessCase);
                        }
                    }
                }
            } catch (Exception e) {
                mLog.debug("Trace 2.4");
                e.printStackTrace();
            }

            if(isRRBClient){

                mLog.debug("Trace 3");
                // Update CaseCache with Division and segment
                return "2176";
            }
            else {
                mLog.debug("Trace 4");
                return productIDToSubstitute;
            }
        }
        else {
            mLog.debug("Trace 5");
            return productIDToSubstitute;
        }
    }*/

    @Override
    public String executeBusinessRules(String productIDToSubstitute, String caseID) throws BusinessRuleExecutionException {
        mLog.debug("Trace 1 >>" + productIDToSubstitute + "<<,>>" + caseID + "<<");

        // First get the list of arracgements allocated to the case.
        BusinessCaseManagementDAO dao = new BusinessCaseManagementDAO();

        BusinessCaseHeader businessCase = dao.retrieveBusinessCase(caseID);

        if(businessCase != null && businessCase.getClientInContextECN() != null && businessCase.getInitiatingChannelID() != null
                && ("397".equalsIgnoreCase(businessCase.getInitiatingChannelID()) || "3".equalsIgnoreCase(businessCase.getInitiatingChannelID()))) {
            mLog.debug("Trace 2");
            BusinessCaseSegment businessCaseSegment = businessCase.getBusinessCaseSegment();
            if(businessCaseSegment != null && businessCaseSegment.getCluster() != null && businessCaseSegment.getDivision() != null){
                if(businessCaseSegment.getCluster().equalsIgnoreCase("200") &&
                    businessCaseSegment.getDivision().equalsIgnoreCase("201")){
                    mLog.debug("Trace 3.0>>");
                    businessCase.setCodHandlerRRB("true");
                    mLog.debug("Trace 3.1>> Setting CodHandlerRRB");
                    dao.cacheBusinessCaseDetailInDB(businessCase);

                    return "2176";
                }
            }
        }
        mLog.debug("Trace 4");
        return productIDToSubstitute;
    }
}
