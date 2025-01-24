const randomNumber = Math.floor(Math.random() * 200)

pm.variables.set("number", randomNumber)

// Body request
// {
//   "name": "Pablo",
//   "email": "pablo{{number}}@gmail.com"
// }