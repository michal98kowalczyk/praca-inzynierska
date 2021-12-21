package com.example.backend.ontology.literal;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.wrapper.LiteralWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiteralService {

    @Autowired
    private LiteralRepository literalRepository;


    public List<Literal> getLiterals() {
        return literalRepository.findAll();
    }



    public Literal getLiteral(Literal literal) {
        Optional<Literal> l = literalRepository.findByValueAndDataType(literal.getValue(),literal.getDataType());
        if (l.isEmpty()) {
            return null;
        }

        return l.get();
    }



    public Literal updateNameSpace(String id, Literal literalToUpdate) {
        Optional<Literal> lCurrent = literalRepository.findById(Long.parseLong(id));
        Optional<Literal> byName = literalRepository.findByValueAndDataType(literalToUpdate.getValue(),literalToUpdate.getDataType());
        if (lCurrent.isPresent() && byName.isEmpty()){
            return literalRepository.save(getEntityToUpdate(lCurrent.get(),literalToUpdate));
        }
        return null;
    }

    private Literal getEntityToUpdate(Literal current, Literal literalToUpdate) {
        return Literal.builder().id(current.getId()).value(literalToUpdate.getValue()).dataType(literalToUpdate.getDataType())
                .statements(current.getStatements()).build();

    }

    public Literal addLiteral(Literal literal) {
        Optional<Literal> literalFromDb = literalRepository.findByValueAndDataType(literal.getValue(),literal.getDataType());
        if (literalFromDb.isPresent()) {
            return null;
        }

        return literalRepository.save(literal);
    }

    public Literal getExistLiteralByValue(String value){
        return literalRepository.findByValue(value).get();
    }

    public Literal getExistLiteralByValueAndDataType(Literal literal){
        return literalRepository.findByValueAndDataType(literal.getValue(),literal.getDataType()).get();
    }

    public LiteralWrapper convert(Literal literal) {
        if (literal != null){
            return LiteralWrapper.builder().id(literal.getId()).value(literal.getValue()).dataType(literal.getDataType()).build();
        }else
            return null;

    }

    public Literal deleteLiteral(String id) {
        Optional<Literal> l = literalRepository.findById(Long.parseLong(id));
        if (l.isEmpty()) {
            return null;
        }

        literalRepository.delete(l.get());
        return l.get();
    }
}
