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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatementService {

    private static final String AUTOMATICALLY_PREDICTION = "Automatically prediction";
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

    public List<Statement> getStatementsForDetails(String statementId) {
        Optional<Statement> byId = statementRepository.findById(Long.parseLong(statementId));
        if(byId.isEmpty())return null;

        Statement statement  = byId.get();
        Resource subject = resourceService.getResource(statement.getSubject().getName());
        Verb verb = verbService.getVerb(statement.getPredicate().getVerb());

        if(statement.isRes){
            Resource resource  = statement.getResource();
            return  statementRepository.findAllBySubjectIdAndPredicateIdAndResourceId(subject.getId(),verb.getId(),resource.getId());
        }else if(statement.isLit){
            Literal literal = statement.getLiteral();
            return  statementRepository.findAllBySubjectIdAndPredicateIdAndLiteralId(subject.getId(),verb.getId(),literal.getId());

        }else{
            return null;
        }

    }

    public List<Statement> getFilteredStatements(String subjectName, String predicateName, String resourceName){
        Resource subject = resourceService.getResource(subjectName);
        Verb verb = verbService.getVerb(predicateName);
        Resource resource = resourceService.getResource(resourceName);


        List<Statement> resultList;
        if(subject==null && verb==null && resource==null ){
            resultList = getStatements();
        }else if(subject!=null && verb!=null && resource!=null ){
            resultList = statementRepository.findAllBySubjectIdAndPredicateIdAndResourceId(subject.getId(), verb.getId(), resource.getId());
        }else if(subject!=null && verb==null && resource==null){
            resultList = statementRepository.findAllBySubjectId(subject.getId());
        }else if(subject==null && verb!=null && resource==null){
            resultList = statementRepository.findAllByPredicateId(verb.getId());
        }else if(subject==null && verb==null && resource!=null){
            resultList = statementRepository.findAllByResourceId(resource.getId());
        }else if(subject!=null && verb!=null && resource==null){
            resultList = statementRepository.findAllBySubjectIdAndPredicateId(subject.getId(), verb.getId());
        }else if(subject!=null && verb==null && resource!=null){
            resultList = statementRepository.findAllBySubjectIdAndResourceId(subject.getId(), resource.getId());
        }else if(subject==null && verb!=null && resource!=null){
            resultList = statementRepository.findAllByPredicateIdAndResourceId(verb.getId(), resource.getId());
        }else{
            return null;
        }

        return resultList;
    }

    public List<Statement> getStatementsByModelId(Long id) {
        return  statementRepository.findAllByModelId(id);
    }

    public List<Statement> getStatementsByPredicateId(Long id) {
        return  statementRepository.findAllByPredicateId(id);
    }

    public List<Statement> getStatementsBySubjectId(Long id) {
        return  statementRepository.findAllBySubjectId(id);
    }

    public List<Statement> getStatementsByResourceId(Long id) {
        return  statementRepository.findAllByResourceId(id);
    }

    public List<Statement> getStatementsBySourceId(Long id) {
        return  statementRepository.findAllBySourceId(id);
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

        Resource source = statement.getSource();

        Resource sourceFromDb = resourceService.addResource(source);
        if (sourceFromDb == null){
            sourceFromDb = resourceService.getExistResourceByName(source.getName());
        }
        statement.setSource(sourceFromDb);

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
        if(properties!=null){
            properties.forEach(property -> property.setStatement(createdStatement));
            propertyService.addProperties(properties);
        }


        runPrediction(createdStatement);

        return createdStatement;
    }

    private void runPrediction(Statement createdStatement) {
        List<Statement> toUpdate = new ArrayList<>();

//        case1 resource in created statement is a subject in another statement
        if (createdStatement.isRes){
            Resource resource = createdStatement.getResource();
            List<Statement> byResourceAsSubject = statementRepository.findAllBySubjectId(resource.getId());

            for (Statement s : byResourceAsSubject) {
                Statement statement = new Statement();
                statement.setSource(resourceService.getExistResourceByName(AUTOMATICALLY_PREDICTION));
                statement.setModel(createdStatement.getModel());
                // add to another model if resource was a model e.g kurczak szkodzi na cukrzyce ..
                Resource rFromS = s.getResource();
                Model existModelByName = modelService.getExistModelByName(rFromS.getName());
                if (existModelByName != null && !existModelByName.getName().equals(createdStatement.getModel().getName()))
                    statement.setModel(existModelByName);


                statement.setSubject(createdStatement.getSubject());
                statement.setPredicate(s.getPredicate());
                statement.setResource(s.getResource());
                Double d = s.getConfidence() * createdStatement.getConfidence();
                int temp = (int)(d*100.0);
                double shortDouble = ((double)temp)/100.0;
                statement.setConfidence(shortDouble);

                toUpdate.add(statement);

            }
        }

        //        case2 subject in created statement is a resource in another statement
        Resource subject = createdStatement.getSubject();
        List<Statement> bySubjectAsResource = statementRepository.findAllByResourceId(subject.getId());

        for (Statement s : bySubjectAsResource) {
            Statement statement = new Statement();
            statement.setSource(resourceService.getExistResourceByName(AUTOMATICALLY_PREDICTION));
            statement.setModel(createdStatement.getModel());

            // add to another model if resource was a model e.g cukrzyca szkodzi na reumatoidalne .. powinno byc dodane do modelu tego drugiego
            Resource rFromS = createdStatement.getResource();
            Model existModelByName = modelService.getExistModelByName(rFromS.getName());

            if (existModelByName != null && !existModelByName.getName().equals( createdStatement.getModel().getName())){
                statement.setModel(existModelByName);
            }


            statement.setSubject(s.getSubject());
            statement.setPredicate(createdStatement.getPredicate());
            statement.setResource(createdStatement.getResource());
            Double d = s.getConfidence() * createdStatement.getConfidence();
            int temp = (int) (d * 100.0);
            double shortDouble = ((double) temp) / 100.0;
            statement.setConfidence(shortDouble);

            toUpdate.add(statement);

        }

        if (!toUpdate.isEmpty()) {

            addStatements(toUpdate);
        }
    }

    public List<Statement> addStatements(List<Statement> statements) {
        List<Statement> saved = new ArrayList<>();

        statements.forEach(statement -> saved.add(addStatement(statement)));
        return  saved;
    }

    public Statement deleteStatement(String id) {
        Optional<Statement> s = statementRepository.findById(Long.parseLong(id));
        if (s.isEmpty()) {
            return null;
        }



        propertyService.deleteProperties(s.get().getProperties());
        statementRepository.delete(s.get());

        if(s.get().isLit)literalService.deleteLiteral(s.get().getLiteral().getId().toString());

        return s.get();
    }

    public List<Statement>  deleteStatements(List<Statement> statements) {
        List<Statement> res = new ArrayList<>();
        statements.forEach(s -> res.add(deleteStatement(s.getId().toString())));
        return res;
    }



    public Statement updateStatement(String id, Statement statementToUpdate) {
        Optional<Statement> sCurrent = statementRepository.findById(Long.parseLong(id));
        if (sCurrent.isPresent() ){
            return statementRepository.save(getEntityToUpdate(sCurrent.get(),statementToUpdate));
        }
        return null;
    }



    private Statement getEntityToUpdate(Statement current, Statement statementToUpdate) {
        current.setConfidence(statementToUpdate.getConfidence());
        return current;
    }


    public StatementOutputWrapper convert(Statement statement) {
        if (statement == null){
            return null;
        }
        StatementOutputWrapper statementOutputWrapper = new StatementOutputWrapper();
        statementOutputWrapper.setId(statement.getId());
        statementOutputWrapper.setSource(resourceService.convert(statement.getSource()));
        statementOutputWrapper.setSubject(resourceService.convert(statement.getSubject()));
        statementOutputWrapper.setPredicate(verbService.convert(statement.getPredicate()));
        statementOutputWrapper.setResource(resourceService.convert(statement.getResource()));
        statementOutputWrapper.setIsRes(statement.isRes());
        statementOutputWrapper.setLiteral(literalService.convert(statement.getLiteral()));
        statementOutputWrapper.setIsLit(statement.isLit());
        statementOutputWrapper.setConfidence(statement.getConfidence());
        statementOutputWrapper.setModelId(statement.getModel().getId());
        statementOutputWrapper.setProperties(propertyService.convert(propertyService.getByStatementId(statement.getId())));
        return statementOutputWrapper;
    }



}
