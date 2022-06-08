import com.sap.gateway.ip.core.customdev.util.Message;
import java.text.SimpleDateFormat;
import groovy.xml.*;
import groovy.json.*;
import java.util.Date;
import java.io.*;

def Message processData(Message message) 
{

    def headers = message.getHeaders();
    def cookie = headers.get("Set-Cookie");
    def body = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    def json 
    
    def data = jsonObject."Last Modified"
    Date date1 = new Date().parse("dd-MM-yyyy", data)
    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd")
    jsonObject."Last Modified" = f.format(date1)
    
    StringBuffer bufferedCookie = new StringBuffer();
    for (Object item : cookie) 
    {
        bufferedCookie.append(item + "; ");      
    }
    message.setHeader("Cookie", bufferedCookie.toString());
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {
        messageLog.setStringProperty("Logging_Cookie", bufferedCookie.toString());
    }
    
        json = JsonOutput.toJson(
            "Codigo" : jsonObject.Code,
            "Descricao": jsonObject.Description,
            "GrupoMateial" : jsonObject.MaterialGroup,
            "CriadoPor" : jsonObject.CreatedBy,
            "ModificadoPor" : jsonObject.ModifiedBy,
            "UltimaModificacao" : jsonObject."Last Modified",
            "Ativo" : jsonObject.Active
            )
            
    message.setBody(JsonOutput.prettyPrint(json))
    
    return message;
}