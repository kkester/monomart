describe("template spec", () => {
  it("passes", () => {
    cy.visit("http://localhost:8888");
  });
});

describe("View purchase history", () => {
  it("Shopper is on Home Page and navigates to the History", () => {
    cy.visit("http://localhost:8888");
    cy.findByLabelText(/history/i).click();
  })
})
