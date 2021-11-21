import '../../styles/verb/VerbList.css';
import Verb from './Verb';

const VerbList = (props) => {

    const verbs = props.verbs.map(v => {
        return <Verb key={v.id} id={v.id} verb={v.verb} />
    });
    return (

        < ul > {verbs}</ul >
    );
}

export default VerbList;
