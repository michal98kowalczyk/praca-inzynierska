package com.example.backend.ontology.statement;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.literal.LiteralService;
import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.model.ModelService;
import com.example.backend.ontology.property.Property;
import com.example.backend.ontology.property.PropertyService;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceService;
import com.example.backend.ontology.verb.Verb;
import com.example.backend.ontology.verb.VerbService;
import com.example.backend.ontology.wrapper.StatementOutputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatementService {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private LiteralService literalService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private VerbService verbService;

    @Autowired
    private PropertyService propertyService;

    public List<Statement> getStatements() {
        return statementRepository.findAll();
    }

    public List<Statement> getStatementsByModelId(Long id) {
        return  statementRepository.findAllByModelId(id);
    }

    public Statement getStatementById(String id) {

        Optional<Statement> byId = statementRepository.findById(Long.parseLong(id));
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }
    public Statement addStatement(Statement statement) {

        Model model = statement.getModel();
        Model modelFromDb = modelService.addModel(model);
        if (modelFromDb == null){
            modelFromDb = modelService.getExistModelByName(model.getName());
        }
        statement.setModel(modelFromDb);

        Literal literal = statement.getLiteral();
        if(literal != null){
            Literal literalFromDb = literalService.addLiteral(literal);
            if (literalFromDb == null){
                literalFromDb = literalService.getExistLiteralByValueAndDataType(literal);
                statement.setLiteral(literalFromDb);
                statement.setLit(true);
            }else {
                statement.setLiteral(literalFromDb);
                statement.setLit(true);
            }
        }else{
            statement.setLiteral(null);
            statement.setLit(false);
        }

        Resource resource = statement.getSubject();
        Resource resourceFromDb = resourceService.addResource(resource);
        if (resourceFromDb == null){
            resourceFromDb = resourceService.getExistResourceByName(resource.getName());
        }
        statement.setSubject(resourceFromDb);

        Verb verb = statement.getPredicate();
        Verb verbFromDb = verbService.addVerb(verb);
        if (verbFromDb == null){
            verbFromDb = verbService.getExistVerbByVerb(verb.getVerb());
        }
        statement.setPredicate(verbFromDb);


        Resource objRes = statement.getResource();
        if(objRes != null){
            Resource objResFromDb = resourceService.addResource(objRes);
            if (objResFromDb == null){
                objResFromDb = resourceService.getExistResourceByName(objRes.getName());
                statement.setResource(objResFromDb);
                statement.setRes(true);
            }else {
                statement.setResource(objResFromDb);
                statement.setRes(true);
            }
        }else {
            statement.setResource(null);
            statement.setRes(false);
        }


        Statement createdStatement = statementRepository.save(statement);
        List<Property> properties = statement.getProperties();
        properties.forEach(property -> property.setStatement(createdStatement));
        propertyService.addProperties(properties);

        return createdStatement;
    }



    public StatementOutputWrapper convert(Statement statement) {
        if (statement == null){
            return null;
        }
        StatementOutputWrapper statementOutputWrapper = new StatementOutputWrapper();
        statementOutputWrapper.setId(statement.getId());
        statementOutputWrapper.setSubject(resourceService.convert(statement.getSubject()));
        statementOutputWrapper.setPredicate(verbService.convert(statement.getPredicate()));
        statementOutputWrapper.setResource(resourceService.convert(statement.getResource()));
        statementOutputWrapper.setIsRes(statement.isRes());
        statementOutputWrapper.setLiteral(literalService.convert(statement.getLiteral()));
        statementOutputWrapper.setIsLit(statement.isLit());
        statementOutputWrapper.setProbablity(statement.getProbability());
        statementOutputWrapper.setModelId(statement.getModel().getId());
        statementOutputWrapper.setProperties(propertyService.convert(propertyService.getByStatementId(statement.getId())));
        return statementOutputWrapper;
    }


}
