import '../../styles/source/SourceForm.css';
import { useState, useEffect } from 'react';

import SourceList from './SourceList';
import BookForm from './BookForm';
import OtherSourceForm from './OtherSourceForm';
import ArticleForm from './ArticleForm';

const SOURCE_NAME = "Źródło";
const BOOK = "book";
const ARTICLE = "article";
const OTHER = "other";
const dict = {};


const SourceForm = () => {

    const [sources, setSources] = useState(null);
    const [type, setType] = useState(BOOK);
    const [source, setSource] = useState("");
    const [properties, setProperties] = useState(dict);

    useEffect(() => {


        fetch('http://localhost:8080/api/sources')
            .then(response => response.json())
            .then(data => {
                setSources(data);
            })
            .catch(err => console.log(err));


    }, [])

    const handleOnSourceChange = (e) => setSource(e.target.value);
    const handleOnTypeChange = (type) => {
        setType(type);
        setSource(null);
        setProperties({});

    }

    const propertyChange = (key, value) => {
        let newDict = properties;
        newDict[key] = value;
        setProperties(newDict);
    }




    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (source === "") {
            alert("Wprowadz źródło");
            return;
        }

        let mappedProperty = [];

        for (let keyTmp in properties) {
            mappedProperty = [...mappedProperty, { key: keyTmp.trim(), value: properties[keyTmp].trim() }];
        }

        const newSource = {
            name: source.trim(),
            nameSpace: { name: SOURCE_NAME },
            properties: mappedProperty
        };
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newSource)
        };
        console.log('newSource ' + JSON.stringify(newSource));

        fetch('http://localhost:8080/api/resource/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                setSource("")
                setProperties({});
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    const renderSwitch = () => {
        switch (type) {
            case BOOK:
                return <BookForm propertyChange={propertyChange} properties={properties} source={source} onSourceChange={handleOnSourceChange} />;
            case ARTICLE:
                return <ArticleForm propertyChange={propertyChange} properties={properties} source={source} onSourceChange={handleOnSourceChange} />

            case OTHER:
                return <OtherSourceForm propertyChange={propertyChange} properties={properties} source={source} onSourceChange={handleOnSourceChange} />

        }
    }



    return (
        <div className="SourceWrapper">
            <h2>Formularz dodania źródła</h2>


            <form className="sourceForm">
                <div className="typeForm">
                    <div className={type === BOOK ? 'isActive' : ''} onClick={() => handleOnTypeChange(BOOK)} >Książka</div>
                    <div className={type === ARTICLE ? 'isActive' : ''} onClick={() => handleOnTypeChange(ARTICLE)} >Artykuł</div>
                    <div className={type === OTHER ? 'isActive' : ''} onClick={() => handleOnTypeChange(OTHER)}>Inne</div>

                </div>

                {renderSwitch()}

                <button onClick={handleOnSubmit}>Dodaj</button>

            </form>

            <section className="availableSources">

                <h2>Dostepne źródła</h2>

                {sources != null ? <SourceList sources={sources.filter(s => s.name !== "Automatically prediction")} /> : undefined}
            </section>
        </div>

    );
}

export default SourceForm;
