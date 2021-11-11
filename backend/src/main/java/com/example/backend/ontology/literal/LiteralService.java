package com.example.backend.ontology.literal;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.wrapper.LiteralWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LiteralService {

    @Autowired
    private LiteralRepository literalRepository;

    public Literal addLiteral(Literal literal) {
        Optional<Literal> literalFromDb = literalRepository.findByValueAndDataType(literal.getValue(),literal.getDataType());
        if (literalFromDb.isPresent()) {
            return null;
        }

        return literalRepository.save(literal);
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
}
