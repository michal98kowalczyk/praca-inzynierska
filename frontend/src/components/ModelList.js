import Model from './Model';
import '../styles/ModelList.css';

const ModelList = (props) => {

    const models = props.models.map(model => {
        return <Model key={model.id} id={model.id} name={model.name} statements={model.statements} />
    });
    return (

        < ul > {models}</ul >
    );
}

export default ModelList;
