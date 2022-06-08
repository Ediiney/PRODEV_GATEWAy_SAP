import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
def Message processData(Message message) {
  
    def body = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    def json 
    def array = []
    
    for ( int i = 0; i < jsonObject.d.results.size(); i++){
        
    json = JsonOutput.toJson(
        Codigo : jsonObject.d.results[i].Codigo,
        Descricao: jsonObject.d.results[i].Descricao,
        GrupoMateial: jsonObject.d.results[i].GrupoMateial,
        CriadoPor: jsonObject.d.results[i].CriadoPor,
        ModificadoPor: jsonObject.d.results[i].ModificadoPor,
        UltimaModificacao: jsonObject.d.results[i].UltimaModificacao,
        Ativo: jsonObject.d.results[i].Ativo
    )
    array.push(json)
    }  
    
    message.setBody(array.toString())
    message.setHeader("Content-Type", "application/json")
    
    return message;
}