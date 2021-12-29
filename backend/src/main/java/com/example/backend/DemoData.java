package com.example.backend;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.literal.LiteralService;
import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.model.ModelService;
import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.namespace.NameSpaceService;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceService;
import com.example.backend.ontology.resourceproperty.ResourceProperty;
import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.verb.Verb;
import com.example.backend.ontology.verb.VerbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoData {


    private static final String SOURCE_CATEGORY = "Źródło";
    @Autowired
    private NameSpaceService ns;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private StatementService statementService;

    @Autowired
    private VerbService verbService;

    @Autowired
    private ModelService modelService;


    @Autowired
    private LiteralService literalService;


    @EventListener
    public void appReady(ApplicationReadyEvent event){
        NameSpace nameSpace = NameSpace.builder().name("Źródło").build();
        NameSpace space = ns.addNameSpace(nameSpace);

        Resource resource = Resource.builder().name("Automatically prediction").nameSpace(ns.getNameSpace("Źródło")).build();
        resourceService.addResource(resource);

        //fillDataBase();
    }

    private void fillDataBase() {
        int sourceCount = 500;
        int categoryCount = 100;
        int resourceCount = 1000;
        int verbCount = 1000;
        int literalCount = 1000;
        int statementCountForModel1 = 4000;
        int statementCountForModel2 = 1000;
        int modelCount = 2;
        List<NameSpace> nameSpaceList = new ArrayList<NameSpace>();
        List<Resource> sourcesList = new ArrayList<Resource>();
        List<Resource> resourceList = new ArrayList<Resource>();
        List<Verb> verbList = new ArrayList<Verb>();
        List<Statement> statementsList = new ArrayList<Statement>();
        List<Literal> literalsList = new ArrayList<Literal>();

        Resource resource;
        for (int i=0;i<sourceCount;i++){
            List<ResourceProperty> resourcePropertyList = new ArrayList<ResourceProperty>();
            ResourceProperty resourceProperty1 = ResourceProperty.builder().key("Autor").value("autor"+(i+1)).build();
            resourcePropertyList.add(resourceProperty1);
            ResourceProperty resourceProperty2 = ResourceProperty.builder().key("Tytuł").value("tytul"+(i+1)).build();
            resourcePropertyList.add(resourceProperty2);
            resource = Resource.builder().name("Test źródło "+(i+1)).nameSpace(ns.getNameSpace(SOURCE_CATEGORY)).properties(resourcePropertyList).build();
            sourcesList.add(resourceService.addResource(resource));
        }


        NameSpace nameSpace;
        for (int i=1;i<categoryCount+1;i++){
            nameSpace = NameSpace.builder().name("category "+i).build();
            nameSpaceList.add(ns.addNameSpace(nameSpace));
        }
        for (int i=0;i< nameSpaceList.size();i++){
            for(int j=0;j<10;j++){
                resource = Resource.builder().name("obiekt "+((j+1)*(i+1))+" c"+(i+1)).nameSpace(nameSpaceList.get(i)).build();
                resourceList.add(resourceService.addResource(resource));
            }

        }

        Verb verb;
        for(int i =0; i<verbCount;i++){
            verb = Verb.builder().verb("test"+(i+1)).build();
            verbList.add(verbService.addVerb(verb));
        }

        Literal literal;
        for(int i =0; i<literalCount;i++){
            literal = Literal.builder().value("lieral "+(i+1)).build();
            literalsList.add(literalService.addLiteral(literal));

        }

        //model 1
        Model model1 = Model.builder().name("model1").build();
        model1 = modelService.addModel(model1);

        for(int i=0;i<100;i++){
            for(int j=0;j<40;j++){
                Statement statement = Statement.builder().model(model1).source(sourcesList.get(i))
                        .subject(resourceList.get(2*i+1)).predicate(verbList.get(i)).resource(resourceList.get(2*i+2)).probability(0.8).build();
                statementsList.add(statement);
            }

        }
        statementService.addStatements(statementsList);



        //model 2
        Model model2 = Model.builder().name("model2").build();
        model2 = modelService.addModel(model2);
        for(int i=0;i<100;i++){
            for(int j=0;j<10;j++){
                Statement statement = Statement.builder().model(model2).source(sourcesList.get(i))
                        .subject(resourceList.get(2*i+1)).predicate(verbList.get(i)).literal(literalsList.get(2*i+1)).probability(0.8).build();
                statementsList.add(statement);
            }

        }
        statementService.addStatements(statementsList);
    }
}
