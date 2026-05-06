import { useEffect, useState } from "react";

function RiskListPage() {

    const [risks, setRisks] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        setRisks([
            {
                id: 1,
                title: "Server Failure",
                status: "OPEN",
                priority: "HIGH"
            },
            {
                id: 2,
                title: "Database Crash",
                status: "IN_PROGRESS",
                priority: "CRITICAL"
            }
        ]);

        setLoading(false);

    }, []);

    if (loading) {
        return <h1>Loading...</h1>;
    }

    return (

        <div className="p-5">

            <h1 className="text-3xl font-bold mb-5">
                Risk Register
            </h1>

            <table className="table-auto border-collapse border w-full">

                <thead>

                    <tr className="bg-gray-200">

                        <th className="border p-2">ID</th>
                        <th className="border p-2">Title</th>
                        <th className="border p-2">Status</th>
                        <th className="border p-2">Priority</th>

                    </tr>

                </thead>

                <tbody>

                    {risks.map((risk) => (

                        <tr key={risk.id}>

                            <td className="border p-2">{risk.id}</td>
                            <td className="border p-2">{risk.title}</td>
                            <td className="border p-2">{risk.status}</td>
                            <td className="border p-2">{risk.priority}</td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
    );
}

export default RiskListPage;