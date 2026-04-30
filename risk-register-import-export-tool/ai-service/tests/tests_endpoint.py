def test_describe_fallback(client, monkeypatch):
    # Simulate a Groq API error by forcing an exception
    def mock_get_completion(self, prompt):
        return {"is_fallback": True, "description": "AI unavailable"}
    
    monkeypatch.setattr("services.groq_client.GroqClient.get_completion", mock_get_completion)

    response = client.post('/ai/describe', json={'title': 'Server Crash'})
    
    # Assert: Should return 200 with the fallback payload
    assert response.status_code == 200
    assert response.json['is_fallback'] is True