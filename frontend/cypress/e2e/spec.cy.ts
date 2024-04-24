describe("View purchase history", () => {
  it("Shopper is on Home Page and navigates to the History", () => {
    cy.visit("localhost:8888");
    cy.findByLabelText(/history/i).click();
    cy.findByText(/purchases/i);
    cy.findByRole("table");
  })
})
