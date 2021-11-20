import '../../styles/statement/StatementItem.css';



const StatementItem = ({ statement }) => {
    const properties = statement.properties.map(property => <><span>{property.key} </span>{property.value} </>)

    return (
        <li>
            <span>{statement.subject.name} </span>
            <span>{statement.predicate.verb} </span>


            {statement.isRes && <span>{statement.resource.name} </span>}
            {statement.isLit && <span>{statement.literal.value} </span>}
            {statement.probablity}<br />
            {properties}

        </li >
    );
}

export default StatementItem;
