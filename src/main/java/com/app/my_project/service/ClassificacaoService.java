package com.app.my_project.service;

import org.springframework.stereotype.Service;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.DenseInstance;

@Service
public class ClassificacaoService {

    private String resultadoClassificacao;

    public String classificacaoTexto(String textToClassification) throws Exception {

        if (resultadoClassificacao != null && resultadoClassificacao.equals(textToClassification)) {
            return resultadoClassificacao;
        }

        DataSource source = new DataSource("C:\\Workspace\\my-project\\src\\main\\resources\\data\\data_arguments.arff");
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);

        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(dataset);
        Instances filteredData = Filter.useFilter(dataset, filter);

        NaiveBayes classifier = new NaiveBayes();
        classifier.buildClassifier(filteredData);
        System.out.println("Classificando o novo exemplo de texto: " + textToClassification);

        Instances emptyDataset = new Instances(dataset, 0);
        emptyDataset.setClassIndex(dataset.classIndex());
        Instance newInstance = new DenseInstance(emptyDataset.numAttributes());
        newInstance.setDataset(emptyDataset);
        newInstance.setValue(emptyDataset.attribute(0), textToClassification);
        emptyDataset.add(newInstance);

        Instances filteredTestData = Filter.useFilter(emptyDataset, filter);

        if (filteredTestData.numInstances() > 0) {
            Instance filteredInstance = filteredTestData.instance(0);
            double label = classifier.classifyInstance(filteredInstance);
            resultadoClassificacao = dataset.classAttribute().value((int) label).toUpperCase();
            System.out.println("Classificação prevista: " + resultadoClassificacao);
        } else {
            System.out.println("O conjunto de dados filtrados está vazio.");
            resultadoClassificacao = "N/A";
        }

        return resultadoClassificacao;
    }

    public String getResultadoClassificacao() {
        return resultadoClassificacao;
    }
}
