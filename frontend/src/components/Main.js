import { useEffect, useState } from 'react';

import '../styles/Main.css';
import ModelList from './model/ModelList';

const Main = () => {

    const [models, setModels] = useState(null);
    const [errorMsg, seterrorMsg] = useState("");

    useEffect(() => {
        fetch('http://localhost:8080/api/models')
            .then(response => {
                return response.json();

            })
            .then(data => {

                setModels(data);
            })
            .catch(err => seterrorMsg("Brak modeli w bazie"));
    }, [])


    return (

        < main >
            <section>
                <h1>Istniejace zbiory:</h1>
                {/* <ModelList models={models} /> */}
                {models !== null ? <ModelList models={models} /> : undefined}
            </section>
        </main >
    );
}

export default Main;
