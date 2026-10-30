class Person{
    name : string
    age : number

    constructor(name: string, age: number){
        this.name = name
        this.age = age
    }
}

let gustave = new Person("Gustave", 32)
console.log(`${gustave.name} is ${gustave.age} years old.`);