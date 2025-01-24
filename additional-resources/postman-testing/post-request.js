pm.test("Response status code is 200", () => pm.response.to.have.status(200))

pm.test("Response time is less than 500ms", () =>
    pm.expect(pm.response.responseTime).to.be.below(500))

pm.test("Body response has the correct fields", () => {
    const response = pm.response.json()
    response.forEach((item) => {
        pm.expect(item).to.have.property("id")
        pm.expect(item).to.have.property("name")
        pm.expect(item).to.have.property("email")
    })
})

pm.test("Body fields are typed correct", () => {
    const response = pm.response.json()
    response.forEach((item) => {
        pm.expect(item.id).to.be.a("number")
        pm.expect(item.name).to.be.a("string")
        pm.expect(item.email).to.be.a("string")
    })
})