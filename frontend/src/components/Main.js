import { useEffect, useState } from 'react';

import '../styles/Main.css';
import ModelList from './ModelList';

const Main = () => {

    const [models, setModels] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/models')
            .then(response => response.json())
            .then(data => {
                setModels(data);
            })
            .catch(err => console.log(err));
    }, [])


    return (

        < main >
            <button>Dodaj nowy model</button>
            <section><h1>Istniejace modele:</h1>
                {/* <ModelList models={models} /> */}
                {models !== null ? <ModelList models={models} /> : undefined}
            </section>
        </main >
    );
}

export default Main;
