import { Product } from "/Users/arwen/UCLL/year2/full-stack/ex/lab-01-typescript-testing-r0991366/src/product";

describe('Product', () => {
  test('should create a product correctly via the constructor', () => {
    const product = new Product(1, "Takis", 2.50, "Mexican chips");
    expect(product.id).toEqual(1);
    expect(product.name).toEqual("Takis");
    expect(product.price).toEqual(2.50);
    expect(product.description).toEqual("Mexican chips")
  });

  test('should update the name successfully using the setter', () => {
    const product = new Product(1, "Takis", 2.50, "Mexican chips");
    product.name = "Takis Lime";
    expect(product.id).toEqual(1);
    expect(product.name).toEqual("Takis Lime");   //do I use getters here?
    expect(product.price).toEqual(2.50);
    expect(product.description).toEqual("Mexican chips")
  });

  test('should update the price successfully using the setter', () => {
    const product = new Product(1, "Takis", 2.50, "Mexican chips");
    product.price = 1.50;
    expect(product.id).toEqual(1);
    expect(product.name).toEqual("Takis");   
    expect(product.price).toEqual(1.50);
    expect(product.description).toEqual("Mexican chips")
  });

  test('should have an undefined description when not set', () => {
    const product = new Product(1, "Takis", 2.50);
    expect(product.id).toEqual(1);
    expect(product.name).toEqual("Takis");   
    expect(product.price).toEqual(2.50);
    expect(product.description).toEqual("undefined")
  });

  test('should allow setting and getting the description', () => {
    const product = new Product(1, "Takis", 2.50);
    product.description = "Tasty spicy chips";
    expect(product.id).toEqual(1);
    expect(product.name).toEqual("Takis");   
    expect(product.price).toEqual(2.50);
    expect(product.description).toEqual("Tasty spicy chips") 
  });

  test('should throw an error when setting a negative price', () => {
    //setting so not constructing? maybe do that to idk
    const product = new Product(1, "Takis", 2.50);
    expect(() => {product.price = -5}).toThrow("Price cannot be negative.")
    expect(product.price).toEqual(2.50); //price remained unchanged
  });

  test('should throw an error when setting an empty name', () => {
    const product = new Product(1, "Takis", 2.50);
    expect(() => {product.name = ""}).toThrow("Name cannot be empty."); //need to wrap this otherwise expect doesnt get the chance to run
    expect(product.price).toEqual(2.50); //price remained unchanged
  });

  test('should throw an error if created with a negative price', () => {
    expect(() => new Product(1, "Takis", -2.50)).toThrow("Price cannot be negative.")
  });

  test('should throw an error if created with an empty name', () => {
    expect(() => new Product(1, "", 2.50)).toThrow("Name cannot be empty.")
  });
});
