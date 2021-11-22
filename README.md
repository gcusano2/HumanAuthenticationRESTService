# Human Authenticator REST Service

The Human Authenticator REST Service is a RESTful web service for verifying clients are human by providing a challenge of 3 integers for the user to sum.

## Running the Service

Make sure you have Java 8 installed and added to your system PATH. To start the server, run the startup.bat script from the command line:

```bash
startup.bat
```

## Usage

To interface with the service, you can send GET and POST requests using an HTTP client like Postman to http://localhost:8080/

(The default port is 8080. To change the port the service is running on, edit the 'server.port' configuration in /src/main/resource/application.properties)

To prompt the service for a challenge, send a GET request to the URL above. You'll receive a prompt with 3 numbers to sum:

*Example: Please sum the following numbers: [21, 12, 9]*

To submit the user's response to the challenge, send a POST request to the same URL with the original prompt and the sum of the 3 integers in JSON format in the body of the request:

```json
{
    "returnedChallenge": "Please sum the following numbers: [21, 12, 9]",
    "returnedSum": 42
}
```

If the returned prompt and sum are BOTH correct, you'll receive the following HTTP 200 response:

*Correct: You are human*

If EITHER field is wrong, you'll receive the following HTTP 400 error:

*Incorrect. Please try again*

# Notes for Smart Equip

## Design of Service

I built this service using Java 8 and Spring Boot, as requested. The total time to implement the service and tests, and build the package was about 5 hours (not including time to set up my Eclipse environment with Spring Boot).

When receiving a request from the client for a prompt, the HAService controller creates a new HumanAuthenticator object containing a List of 3 randomly generated integers, the sum of the integers, and the full challenge prompt to be sent to the client. The object is then stored in an embedded H2 database for later verification, before sending the challenge prompt to the client.

When the client send back the response. The HAService stores the fields in a ClientResponse object containing the original prompt and the sum of the numbers. The service then retrieves the original prompt and sum from the database into a new HumanAuthenticator object. If both the client and stored values are the same, an HTTP 200 request is returned to the client. If not, a HTTP 400 request is sent.

## Testing

I included 2 unit tests and 2 integration tests. One unit test is a SmokeTest to make sure the application context starts. The other tests that the prompt is built correctly. The 2 integration tests a successful GET request is sent and the prompt is returned, and that a bad POST request returns the correct HTTP 400 error. I don't have much experience in writing tests with Spring, and was having trouble testing a successful POST request since I would need to first mockup values in the database to test against. With more time, I would've liked to read more about Spring unit testing to test against successful values and inaccurate values.

## Ideas for Improvement

As described above, with more time I would've liked to implement more robust unit/integration tests.

One thing I noticed while building the application was that an HTTP 200 response would be sent if additional json fields were added in the body of the client's POST request as long as the other 2 fields the service expected were good values. With more time I would check for these extra fields and send an HTTP 400 response if they were present.

Lastly, I would want to allow for the service to consume data in XML as well as JSON. I believe Spring Boot may allow for this by default, but I didn't have enough time test.
