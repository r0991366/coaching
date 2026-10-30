export class Product {
    private _id: number;
    private _name: string = "";
    private _price: number = 0;
    private _description?: string;

    constructor(id: number, name: string, price: number, description?: string) {
        //using destructuring here
        this._id = id;
        this.name = name;  //<-properties, will go via setter
        this.price = price;
        this.description = description ?? "undefined" 
    }

    public get id() {
        return this._id
    }

    public get name() {
        return this._name
    }

    public get price() {
        return this._price
    }

    public get description() {
        return this._description ?? "";
    }

    public set name(newName: string) {
        if (newName == "") {throw new Error("Name cannot be empty.");}
        this._name = newName;
    }

    public set price(newPrice: number) {
        if (newPrice < 0) {throw new Error("Price cannot be negative.")}
        this._price = newPrice;
    }

    public set description(newDesc: string) {
        this._description = newDesc
    }
}

const product = new Product(1, "Takis", 2.50, "Mexican chips");