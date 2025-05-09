function validateCardNumber(cardNumber) {
    const cardNumberStr = cardNumber.toString();

    // Define the rules for each bandeira
    const bandeiras = [
        { name: "Visa", regex: /^4/ },
        { name: "MasterCard", regex: /^(5[1-5]|2(2[2-9]|[3-6][0-9]|7[01]|720))/ },
        { name: "Elo", regex: /^(4011|4312|4389|45|50|627780|636297|636368)/ },
        { name: "American Express", regex: /^3[47]/ },
        { name: "Discover", regex: /^(6011|65|64[4-9])/ },
        { name: "Hipercard", regex: /^6062/ }
    ];

    // Check the bandeira
    const bandeira = bandeiras.find(b => b.regex.test(cardNumberStr));
    if (!bandeira) {
        return { valid: false, bandeira: null, message: "Invalid card number or unsupported bandeira." };
    }

    // Validate the card number using the Luhn algorithm
    const isValid = luhnCheck(cardNumberStr);
    return {
        valid: isValid,
        bandeira: bandeira.name,
        message: isValid ? "Valid card number." : "Invalid card number."
    };
}

// Luhn algorithm implementation
function luhnCheck(cardNumber) {
    let sum = 0;
    let shouldDouble = false;

    // Process digits from right to left
    for (let i = cardNumber.length - 1; i >= 0; i--) {
        let digit = parseInt(cardNumber[i]);

        if (shouldDouble) {
            digit *= 2;
            if (digit > 9) digit -= 9;
        }

        sum += digit;
        shouldDouble = !shouldDouble;
    }

    return sum % 10 === 0;
}

// Example usage
const cardNumber = "3554031585609620"; // Replace with the card number to test
const result = validateCardNumber(cardNumber);
console.log(result);