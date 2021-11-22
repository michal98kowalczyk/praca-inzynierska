import '../../styles/resource/ResourceList.css';
import Resource from './Resource';

const ResourceList = (props) => {

    const resources = props.resources.map(r => {
        return <Resource key={r.id} id={r.id} name={r.name} category={r.namespace} categories={props.categories} />
    });
    return (

        < ul > {resources}</ul >
    );
}

export default ResourceList;
