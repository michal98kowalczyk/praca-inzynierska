import '../../styles/statement/StatementForm.css';
import { useState } from 'react';

const StatementForm = () => {

    const [modelName, setModelName] = useState("");
    const [subject, setSubject] = useState("");
    const [subjectCategory, setSubjectCategory] = useState("");
    const [predicate, setPredicate] = useState("");
    const [resource, setResource] = useState("");
    const [resourceCategory, setResourceCategory] = useState("");
    const [literal, setLiteral] = useState("");
    const [probability, setProbability] = useState();
    const [properties, setProperties] = useState([{ key: "", value: "" }])
    let handleChange = (i, e) => {
        let newFormValues = [...properties];
        newFormValues[i][e.target.name] = e.target.value;
        setProperties(newFormValues);
    }

    let addFormFields = () => {
        setProperties([...properties, { key: "", value: "" }])
    }

    let removeFormFields = (i) => {
        let newFormValues = [...properties];
        newFormValues.splice(i, 1);
        setProperties(newFormValues)
    }

    const handleOnModelNameChange = (e) => {
        setModelName(e.target.value);
    }
    const handleOnSubjectChange = (e) => {
        setSubject(e.target.value);
    }
    const handleOnPredicateChange = (e) => {
        setPredicate(e.target.value);
    }
    const handleOnResourceChange = (e) => {
        setResource(e.target.value);
    }
    const handleOnLiteralChange = (e) => {
        setLiteral(e.target.value);
    }
    const handleOnProbabilityChange = (e) => {
        setProbability(e.target.value);
    }
    const handleOnSubjectCategoryChange = (e) => {
        setSubjectCategory(e.target.value);
    }
    const handleOnResourceCategoryChange = (e) => {
        setResourceCategory(e.target.value);
    }

    const handleOnClick = (e) => {
        e.preventDefault();

        if (modelName === "") {
            alert("Wprowadz nazwe modelu");
            return;
        }
        if (subject === "") {
            alert("Wprowadz subject");
            return;
        }

        if (subjectCategory === "") {
            alert("Wprowadz subject category");
            return;
        }

        if (predicate === "") {
            alert("Wprowadz predicate");
            return;
        }

        if (resource === "" && literal === "") {
            alert('wprowadz literal lub resorce');
            return;
        }
        if (resource !== "" && literal !== "") {
            alert('wprowadz tylko jedno');
            return;
        }
        if (resource !== "" && resourceCategory === "") {
            alert('wprowadz resource category');
            return;
        }
        if (probability === undefined) {
            alert("Wprowadz probability");
            return;
        }

        const statement = {
            model: { name: modelName },
            properties: properties,
            subject: {
                name: subject,
                nameSpace: { name: subjectCategory }
            },
            predicate: { verb: predicate },
            probability: probability
        }
        if (literal !== "") {
            statement.literal = {
                value: literal,
                dataType: "string"
            }
        }
        if (resource !== "") {
            statement.resource = {
                name: resource,
                nameSpace: { name: resourceCategory }
            }
        }
        // console.log('statement ' + JSON.stringify(statement));


        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(statement)
        };
        fetch('http://localhost:8080/api/statements/add', requestOptions)
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));



    }




    return (
        <form>
            <label htmlFor="m_name">Nazwa modelu

                <input id="m_name" type="text" placeholder="model" value={modelName} onChange={handleOnModelNameChange} />
            </label>
            <label htmlFor="subject">Subject
                <input id="subject" type="text" placeholder="subject" value={subject} onChange={handleOnSubjectChange} />
            </label>
            <label htmlFor="subjectCategory">subjectCategory
                <input id="subjectCategory" type="text" placeholder="subjectCategory" value={subjectCategory} onChange={handleOnSubjectCategoryChange} />
            </label>
            <label htmlFor="predicate">Predicate
                <input id="predicate" type="text" placeholder="predicate" value={predicate} onChange={handleOnPredicateChange} />
            </label>
            <label htmlFor="resource">Resource
                <input id="resource" type="text" placeholder="resource" value={resource} onChange={handleOnResourceChange} />
            </label>
            <label htmlFor="resourceCategory">resourceCategory
                <input id="resourceCategory" type="text" placeholder="resourceCategory" value={resourceCategory} onChange={handleOnResourceCategoryChange} />
            </label>
            <label htmlFor="literal">Literal
                <input id="literal" type="text" placeholder="literal" value={literal} onChange={handleOnLiteralChange} />
            </label>
            <label htmlFor="probability">Probability
                <input id="probability" type="number" step="0.01" min="-1" max="1" placeholder="probability" value={probability} onChange={handleOnProbabilityChange} />
            </label>

            {properties.map((element, index) => (
                <div className="form-inline" key={index}>
                    <label>Key</label>
                    <input type="text" name="key" value={element.key || ""} onChange={e => handleChange(index, e)} />
                    <label>Value</label>
                    <input type="text" name="value" value={element.value || ""} onChange={e => handleChange(index, e)} />
                    {
                        index ?
                            <button type="button" className="button remove" onClick={() => removeFormFields(index)}>Remove</button>
                            : null
                    }
                </div>
            ))}
            <button className="button add" type="button" onClick={() => addFormFields()}>Dodaj parametr</button>
            <button onClick={handleOnClick}>Dodaj</button>

        </form>
    );
}

export default StatementForm;
