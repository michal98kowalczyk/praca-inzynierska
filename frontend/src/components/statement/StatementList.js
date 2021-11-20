import '../../styles/statement/StatementList.css';
import StatementItem from './StatementItem';

const StatementList = (props) => {

    const statements = props.statements.map(statement => {
        return <StatementItem key={statement.id} statement={statement} />
    });


    return (
        <ul>{statements}</ul>
    );
}

export default StatementList;
