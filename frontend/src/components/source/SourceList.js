import '../../styles/source/SourceList.css';
import Source from './Source';

const SourceList = (props) => {

    const sources = props.sources.map(r => {
        return <Source
            key={r.id}
            id={r.id}
            name={r.name}
            properties={r.properties}
        />
    });
    return (

        < ul > {sources}</ul >
    );
}

export default SourceList;
