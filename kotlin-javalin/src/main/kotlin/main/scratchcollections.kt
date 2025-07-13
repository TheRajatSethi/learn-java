package main

fun main(){
    val data = arrayOf("""Water\Aqua\Eau, Cyclopentasiloxane, Bis-Diglyceryl Polyacyladipate-2, Butylene Glycol, Ppg-2 Myristyl Ether Propionate, Cetyl Alcohol, Peg-40 Stearate, Butyrospermum Parkii (Shea Butter), Squalane, Glyceryl Stearate, Sorbitan Stearate, Epilobium Angustifolium Extract, Yeast Extract\Faex\Extrait De Levure, Beta-Carotene, Sodium Hyaluronate, Citrus Grandis (Grapefruit) Peel Oil, Tocopheryl Acetate, Methyl Glucose Sesquistearate, Pelargonium Graveolens (Geranium) Flower Oil, Dimethicone Crosspolymer, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Carbomer, Peg-20 Methyl Glucose Sesquistearate, C12-15 Alkyl Ethylhexanoate, Tetrasodium Edta, Sodium Hydroxide, Panthenol, Magnesium Ascorbyl Phosphate, Limonene, Citronellol, Geraniol, Linalool, Chlorphenesin, Phenoxyethanol <ILN39205>""",
    """Aqua/water/eau, Saccharomyces/rice Ferment Filtrate, Glycerin, Propanediol, Dimethicone, Squalane, Diisostearyl Malate, Behenyl Alcohol, Myristyl Myristate, Dipentaerythrityl Hexahydroxystearate, Bis-diglyceryl Polyacyladipate-2, Tridecyl Trimellitate, Inositol, Sorbitan Tristearate, Beheneth-20, Camellia Sinensis Leaf Extract, Cladosiphon Okamuranus Extract, Chondrus Crispus Extract, Betaphycus Gelatinum Extract, Oryza Sativa (Rice) Bran Extract, Panax Ginseng Root Extract, Origanum Majorana Leaf Extract, Thymus Serpyllum Extract, Malva Sylvestris (Mallow) Flower Extract, Sodium Hyaluronate, Gold, Sericin, Phytosteryl Macadamiate, Tocopherol, Hydroxyethyl Acrylate/sodium Acryloyldimethyl Taurate Copolymer, Sorbitan Isostearate, Polysorbate 60, Butylene Glycol, Dimethicone/vinyl Dimethicone Crosspolymer, Sodium Benzoate, Gluconolactone, Ethylhexylglycerin, Parfum/fragrance, Alcohol, Phenoxyethanol, Citral, Limonene, Linalool, Mica, Tin Oxide, Titanium Dioxide (Ci 77891), Violet 2 (Ci 60725)""",
    """Aqua/Water/Eau, Propanediol, Caprylic/Capric Glycerides, Squalane, Butylene Glycol, Glycerin, Argania Spinosa Kernel Oil, 1,2-Hexanediol, Hydroxyethyl Acrylate/Sodium Acryloyldimethyl Taurate Copolymer, Hydroxyacetophenone, Isohexadecane, Allantoin, Linoleic Acid, Phospholipids, Phytosterols, Caprylyl Glycol, Polyglyceryl-10 Stearate, Hydrolyzed Opuntia Ficus-Indica Flower Extract, Polysorbate 60, Sorbitan Isostearate, Hyaluronic Acid, Hydrolyzed Hyaluronic Acid, Sodium Hyaluronate, Chlorella Vulgaris Extract, Lactic Acid.""",
    """Water/Aqua/Eau Glycerin Butyrospermum Parkii (Shea) Butter Caprylic/Capric Triglyceride C13-15 Alkane Cetearyl Alcohol Pentaerythrityl Tetraisostearate 1,2-Hexanediol Bis-Diglyceryl Polyacyladipate-1 Diisostearyl Malate Xylitylglucoside Honey Extract Betaine Panthenol Hydrogenated Rapeseed Oil Helianthus Annuus (Sunflower) Seed Oil Unsaponifiables Ceramide Np Propolis Extract Royal Jelly Extract Tocopherol Ficus Carica (Fig) Fruit Extract Hippophae Rhamnoides Oil Glucose Bisabolol Xylitol Anhydroxylitol Cetearyl Glucoside Triolein Acrylates/C10-30 Alkyl Acrylate Crosspolymer Hydroxyethyl Acrylate/Sodium Acryloyldimethyl Taurate Copolymer Arginine Flavor (Aroma)* Hydroxyacetophenone Xanthan Gum Glyceryl Dioleate Sodium Dilauramidoglutamide Lysine Sodium Phytate Sorbitan Isostearate Citric Acid Potassium Sorbate Sodium Benzoate""")

//    val d = data.sumOf { it.split(",").count() }
//    val unique = data.map{it.split(",").toSet()}.flatten().toSet().count()

    val graph = data
        .map{it.split(",")}
        .flatten()
        .map{it -> it.lowercase()}
        .map{it.trim()}
        .map{it.replace("/","")}
        .map{it.replace("\\","")}
    val howMany = graph.groupingBy{it}.eachCount()
    println(howMany)

}